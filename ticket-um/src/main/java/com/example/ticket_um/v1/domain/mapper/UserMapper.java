package com.example.ticket_um.v1.domain.mapper;

import com.example.ticket_um.v1.domain.dto.EditUserReq;
import com.example.ticket_um.v1.domain.dto.UserReq;
import com.example.ticket_um.v1.domain.dto.UserResp;
import com.example.ticket_um.v1.domain.model.Users;
import com.example.ticket_um.v1.domain.commons.ContactDto;
import com.example.ticket_um.v1.service.UserService;
import com.example.ticket_utils.v1.domain.dto.UserDtl;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import java.util.List;
import java.util.Objects;
import static org.springframework.util.CollectionUtils.isEmpty;

@Mapper(uses = {RoleMapper.class, PermissionMapper.class})
public abstract class UserMapper {

  @Lazy
  @Autowired
  protected UserService userService;

  @Mapping(target = "contacts", ignore = true)
  public abstract Users toUser(UserReq req);

  @BeforeMapping
  protected void beforeToUser(UserReq req, @MappingTarget Users user) {
    if (!isEmpty(req.getContacts())) {
      req.getContacts().forEach(c -> user.addContact(c.getType(), c.getContact()));
    }
  }

  @Mapping(target = "contacts", ignore = true)
  public abstract void update(EditUserReq req, @MappingTarget Users user);

  @BeforeMapping
  protected void beforeUpdate(EditUserReq req, @MappingTarget Users user) {

    if (!isEmpty(req.getContacts())) {
      var contactIdsReq = req.getContacts().stream()
          .map(ContactDto::getId)
          .filter(Objects::nonNull)
          .toList();
      //Delete contacts
      user.getContacts().removeIf(c -> !contactIdsReq.contains(c.getId()));
      //Add or edit contacts
      req.getContacts().forEach(c -> {
        if (c.getId() == null) {
          // add contact
          user.addContact(c.getType(), c.getContact());
        } else {
          //Edit contact
          user.getContacts().stream()
              .filter(uc -> uc.getId().equals(c.getId()))
              .findFirst()
              .ifPresent(uc -> uc.setContact(c.getContact()));
        }
      });
    } else {
      //Clear all contacts
      user.getContacts().clear();
    }
  }

  @Named("toUserResp")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "active", target = "active")
  public abstract UserResp toUserResp(Users user);

  @IterableMapping(qualifiedByName = "toUserResp")
  public abstract List<UserResp> toUserRespList(List<Users> users, @Context boolean withPrntUser);

  public List<UserResp> toUserRespList(List<Users> users) {
    return toUserRespList(users, true);
  }

  public abstract UserDtl touUserDtl(Users user);
}

