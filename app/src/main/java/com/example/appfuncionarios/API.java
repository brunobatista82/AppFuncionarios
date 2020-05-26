package com.example.appfuncionarios;

public class API {

    private static final String ROOT_URL = "http://localhost/TI93/FuncionariosAPI/v1/Api.php?apicall=getFuncionario";

    public static final String URL_CREATE_FUNCIONARIO = ROOT_URL + "createFuncionario";
    public static final String URL_READ_FUNCIONARIO = ROOT_URL + "getFuncionario";
    public static final String URL_UPDATE_FUNCIONARIO = ROOT_URL + "updateFuncionario";
    public static final String URL_DELETE_FUNCIONARIO = ROOT_URL + "deleteFuncionario&id=";
}
