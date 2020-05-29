package com.psk.demo.Entity;

import javax.persistence.*;

@Entity
@Table(name = "limit")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "limit_id")
    private Long limitId;

    @Column(name = "is_global")
    private boolean isGlobal;

    @Column(name = "days_in_year")
    private int daysInYear;

    @Column(name = "days_in_month")
    private int daysInMonth;

    @Column(name = "days_in_row")
    private int daysInRow;

    public Long getId() {
        return this.limitId;
    }
    public boolean getIsGlobal() { return this.isGlobal; }
    public int getDaysInYear() { return this.daysInYear; }
    public int getDaysInMonth() { return this.daysInMonth; }
    public int getDaysInRow() { return this.daysInRow; }

    public void setId(Long id) { this.limitId = id; }
    public void setIsGlobal(boolean isGlobal) { this.isGlobal = isGlobal; }
    public void setDaysInYear(int days) { this.daysInYear = days; }
    public void setDaysInMonth(int days) { this.daysInMonth = days; }
    public void setDaysInRow(int days) { this.daysInRow = days; }
}
