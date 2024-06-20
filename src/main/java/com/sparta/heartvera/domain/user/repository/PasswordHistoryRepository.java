package com.sparta.heartvera.domain.user.repository;


import com.sparta.heartvera.domain.user.entity.PasswordHistory;
import com.sparta.heartvera.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

  List<PasswordHistory> findTop3ByUserOrderByChangedAtDesc(User user);

}
