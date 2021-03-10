package ru.anikin.rzd.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.anikin.rzd.models.Employee;

import java.util.Collection;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    @Query("from Employee e where e.name like concat(:name, '%') ")
    Collection<Employee> findByName(@Param("name") String name);
}
