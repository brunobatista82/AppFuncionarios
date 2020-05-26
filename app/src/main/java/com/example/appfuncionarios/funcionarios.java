package com.example.appfuncionarios;

public class funcionarios {

    private int id;
    private String cargo, atividades;
    private Double salario;

    public funcionarios(int id, String cargo, String atividades, String salario) {
        this.id = id;
        this.cargo = cargo;
        this.atividades = atividades;
        this.salario = salario;
    }

    public int getId() {return id;}
    public String getCargo() {return cargo;}
    public String getAtividades() {return atividades;}
    public int getSalario() {return salario;}
}
