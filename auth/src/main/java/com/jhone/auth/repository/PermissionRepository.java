package com.jhone.auth.repository;

import com.jhone.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    @Query("SELECT p from Permission  p WHERE p.description =:description")
    Permission FindByDescription(@Param("description") String description);

}
