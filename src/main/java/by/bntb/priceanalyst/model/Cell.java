package by.bntb.priceanalyst.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long cellId;

    @ManyToOne
    @JoinColumn(name = "row_id", nullable = false)
    private Row row;

    private String value;
}
