package ru.letry.restaurants.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.letry.restaurants.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.dateTime >= :startDate AND v.dateTime < :endDate ORDER BY v.dateTime DESC")
    List<Vote> getBetweenHalfOpen(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.dateTime >= :startDate AND v.dateTime < :endDate ORDER BY v.dateTime DESC")
    List<Vote> getUserVotesByDay(@Param("userId") int userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

//    @Query("SELECT v FROM Vote v ORDER BY v.dateTime DESC")
//    List<Vote> getAll();
}
