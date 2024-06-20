package com.sparta.heartvera.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pw_history")
@Getter
@NoArgsConstructor
public class PasswordHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pw_history_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_seq", nullable = false)
  private User user;

  @Column(name = "used_password", nullable = false)
  private String usedPassword;

  @Column(name = "change_date", nullable = false)
  private LocalDateTime changeDate;

  public PasswordHistory(User user, String usedPassword, LocalDateTime changeDate) {
    this.user = user;
    this.usedPassword = usedPassword;
    this.changeDate = changeDate;
  }

}
