package br.com.pwbe.conecta_saude.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import br.com.pwbe.conecta_saude.enums.StatusAgendamento;

@Entity
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Usuario paciente;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Usuario profissional;

    private LocalTime horaInicio;
    private LocalTime horaFim;

    private String observacao;
    private LocalDate dataConsulta;
    private LocalDate dataCriacao;

    //Getters e Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public StatusAgendamento getStatus() {return status;}
    public void setStatus(StatusAgendamento status) {this.status = status;}

    public Usuario getPaciente() {return paciente;}
    public void setPaciente(Usuario paciente) {this.paciente = paciente;}

    public Usuario getProfissional() {return profissional;}
    public void setProfissional(Usuario profissional) {this.profissional = profissional;}

    public LocalTime getHoraInicio() {return horaInicio;}
    public void setHoraInicio(LocalTime horaInicio) {this.horaInicio = horaInicio;}

    public LocalTime getHoraFim() {return horaFim;}
    public void setHoraFim(LocalTime horaFim) {this.horaFim = horaFim;}

    public String getObservacao() {return observacao;}
    public void setObservacao(String observacao) {this.observacao = observacao;}

    public LocalDate getDataConsulta() {return dataConsulta;}
    public void setDataConsulta(LocalDate dataConsulta) {this.dataConsulta = dataConsulta;}

    public LocalDate getDataCriacao() {return dataCriacao;}
    public void setDataCriacao(LocalDate dataCriacao) {this.dataCriacao = dataCriacao;}
}
