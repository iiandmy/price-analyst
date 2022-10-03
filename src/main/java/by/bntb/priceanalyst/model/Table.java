package by.bntb.priceanalyst.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long tableId;

    @ManyToOne
    @JoinColumn(name = "page_id", nullable = false)
    private Page page;

    @OneToMany(mappedBy = "table")
    private Set<Row> rows;

    private String name;
}
