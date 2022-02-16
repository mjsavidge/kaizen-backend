package com.kaizen.validation.token;

import com.kaizen.model.UserModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "confirmation_token")
@Table(name = "confirmation_token")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, name = "token")
    private String token;
    @Column(nullable = false, name = "created")
    private LocalDateTime createdAt;
    @Column(nullable = false, name = "expires")
    private LocalDateTime expiresAt;
    @Column(name = "confirmed")
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name ="users_id")
    private UserModel userModel;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt,  UserModel userModel) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.userModel = userModel;
    }
}
