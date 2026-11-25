package br.com.pwbe.conecta_saude.model;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
public class HorarioAtendimento{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Usuario profissional;

    @Enumerated(EnumType.STRING)
    private DayOfWeek diaSemana;

    private LocalTime horaInicio;
    private LocalTime horaFim;

    //Getters e Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Usuario getProfissional() {return profissional;}
    public void setProfissional(Usuario profissional) {this.profissional = profissional;}

    public DayOfWeek getDia_semana() {return diaSemana;}
    public void setDia_semana(DayOfWeek dia_semana) {this.diaSemana = diaSemana;}

    public LocalTime getHoraInicio() {return horaInicio;}
    public void setHoraInicio(LocalTime horaInicio) {this.horaInicio = horaInicio;}

    public LocalTime getHoraFim() {return horaFim;}
    public void setHoraFim(LocalTime horaFim) {this.horaFim = horaFim;}
}
