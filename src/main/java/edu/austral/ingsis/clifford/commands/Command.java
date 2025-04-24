package edu.austral.ingsis.clifford.commands;

public sealed interface Command permits Cd, Ls, Rm, Mkdir, Pwd, Touch {

    String execute();
}
