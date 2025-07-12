package com.example.ticket_um.v1.repository;

import com.example.ticket_um.v1.domain.dto.UserSearchReq;
import com.example.ticket_um.v1.domain.model.Users;
import com.example.ticket_um.v1.domain.model.UserContact;
import com.example.ticket_um.v1.domain.model.enums.ContactType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@Data
@RequiredArgsConstructor
public class UserSpec implements Specification<Users> {

  private final UserSearchReq searchQuery;

  @Override
  public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    var predicates = new ArrayList<Predicate>();

    if (searchQuery.getActive() != null) {
      predicates.add(criteriaBuilder.equal(root.get("active"), searchQuery.getActive()));
    }

    if (hasText(searchQuery.getUsername())) {
      predicates.add(criteriaBuilder.like(root.get("username"), searchQuery.getUsername().toUpperCase() + "%"));
    }

    if (hasText(searchQuery.getFirstName())) {
      predicates.add(criteriaBuilder.like(root.get("firstName"), searchQuery.getFirstName() + "%"));
    }

    if (hasText(searchQuery.getLastName())) {
      predicates.add(criteriaBuilder.like(root.get("lastName"), searchQuery.getLastName() + "%"));
    }

    if (hasText(searchQuery.getIdNumber())) {
      predicates.add(criteriaBuilder.equal(root.get("idnumber"), searchQuery.getIdNumber()));
    }

    if (hasText(searchQuery.getEmail())) {
      var subquery = query.subquery(UserContact.class);
      var subRoot = subquery.from(UserContact.class);
      subquery.select(subRoot);
      subquery.where(
          criteriaBuilder.and(
              criteriaBuilder.equal(subRoot.get("contact"), searchQuery.getEmail().toLowerCase()),
              criteriaBuilder.equal(subRoot.get("type"), ContactType.EMAIL),
              criteriaBuilder.equal(subRoot.get("user"), root)
          )
      );
      predicates.add(criteriaBuilder.exists(subquery));
    }

    if (hasText(searchQuery.getPhone())) {
      var subquery = query.subquery(UserContact.class);
      var subRoot = subquery.from(UserContact.class);
      subquery.select(subRoot);
      subquery.where(
          criteriaBuilder.and(
              criteriaBuilder.equal(subRoot.get("contact"), searchQuery.getPhone()),
              criteriaBuilder.equal(subRoot.get("type"), ContactType.MOBILE),
              criteriaBuilder.equal(subRoot.get("user"), root)
          )
      );
      predicates.add(criteriaBuilder.exists(subquery));
    }

    if (!isEmpty(searchQuery.getPermTypeIds())) {
      var subquery = query.subquery(Users.class);
      var subRoot = subquery.from(Users.class);
      var permTypePath = subRoot.get("roles").get("permissions").get("type").get("id");
      subquery.select(subRoot.get("id"));
      subquery.where(
          criteriaBuilder.and(
              criteriaBuilder.equal(root, subRoot),
              permTypePath.in(searchQuery.getPermTypeIds())
          )
      );
      predicates.add(criteriaBuilder.exists(subquery));
    }

    if (!isEmpty(searchQuery.getPermissionIds())) {
      var subquery = query.subquery(Users.class);
      var subRoot = subquery.from(Users.class);
      subquery.select(subRoot);
      subquery.where(
          criteriaBuilder.and(
              subRoot.get("roles").get("permissions").get("id").in(searchQuery.getPermissionIds()),
              criteriaBuilder.equal(root, subRoot)
          )
      );
      predicates.add(criteriaBuilder.exists(subquery));
    }
    return predicates.stream().reduce(criteriaBuilder::and).orElse(criteriaBuilder.and());
  }
}