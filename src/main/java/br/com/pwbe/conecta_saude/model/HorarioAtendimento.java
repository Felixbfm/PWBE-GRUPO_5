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

    @ManyToOne
    @JoinColumn(name = "unidade_saude_id")
    private UnidadeSaude unidadeSaude;

    @Enumerated(EnumType.STRING)
    private DayOfWeek diaSemana;

    private LocalTime horaInicio;
    private LocalTime horaFim;

    //Getters e Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Usuario getProfissional() {return profissional;}
    public void setProfissional(Usuario profissional) {this.profissional = profissional;}

    public UnidadeSaude getUnidadeSaude() {return unidadeSaude;}
    public void setUnidadeSaude(UnidadeSaude unidadeSaude) {this.unidadeSaude = unidadeSaude;}

    public DayOfWeek getDiaSemana() {return diaSemana;}
    public void setDiaSemana(DayOfWeek diaSemana) {this.diaSemana = diaSemana;}

    public LocalTime getHoraInicio() {return horaInicio;}
    public void setHoraInicio(LocalTime horaInicio) {this.horaInicio = horaInicio;}

    public LocalTime getHoraFim() {return horaFim;}
    public void setHoraFim(LocalTime horaFim) {this.horaFim = horaFim;}
}
