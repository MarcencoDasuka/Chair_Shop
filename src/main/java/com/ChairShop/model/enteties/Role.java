package com.ChairShop.model.enteties;

import com.ChairShop.service.model.IamServiceUserRole;
import com.ChairShop.utils.enum_convertor.UserRoleTypeConvertor;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "user_system_role", nullable = false, updatable = false )
    @Convert(converter = UserRoleTypeConvertor.class)
    private IamServiceUserRole userSystemRole;

    @Column()
    private boolean active;

    @Column(name = "created_by")
    private String created_by;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "roles")
    private Set<User> users = new HashSet<>();


}
