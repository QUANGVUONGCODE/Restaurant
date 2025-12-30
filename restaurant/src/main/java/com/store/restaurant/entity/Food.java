package com.store.restaurant.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "foods")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @Column(name = "price", nullable = false)
    Float price;

    @Column(name = "thumbnail")
    String thumbnail;

    @Column(name = "description",columnDefinition = "LONGTEXT")
    String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @Column(name = "best_seller")
    Boolean bestSeller = false;

    @Column(name = "active")
    Boolean active;


    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "food_sections",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns = @JoinColumn(name = "section_id")
    )
    Set<Section> sections = new HashSet<>();

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
