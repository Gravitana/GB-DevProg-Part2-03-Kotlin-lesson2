package ru.gravitana.homework

data class Person(var name: String, var phone: String, var email: String)

var person = Person("", "", "")

sealed interface Command {
    fun isValid(): Boolean
}

class Error(val error: String) : Command {
    override fun isValid(): Boolean {
        return true
    }
}

class Exit() : Command {
    override fun isValid(): Boolean {
        return true
    }
}

class Help() : Command {
    override fun isValid(): Boolean {
        return true
    }
}

class Show() : Command {
    override fun isValid(): Boolean {
        return true
    }
}

class AddPhone(val name: String, val phone: String) : Command {
    override fun isValid(): Boolean {
        return phone.matches(Regex("""\+?[0-9]+"""))
    }
}

class AddEmail(val name: String, val email: String) : Command {
    override fun isValid(): Boolean {
        return email.contains("@") && email.contains(".")
    }
}

fun readCommand(): Command {
    println("Введите команду")
    val userInput: String = readlnOrNull().toString()

    if (userInput.isBlank()) {
        return Error("Command is empty")
    }

    when (userInput) {
        "exit", "q" -> return Exit()
        "help" -> return Help()
        "show" -> return Show()
    }

    if (!userInput.startsWith("add ")) {
        return Error("This line is not a Command")
    }

    val inputLines = userInput.split(" ")
    val name = inputLines[1]
    val currentCommand = inputLines[2]
    val value = inputLines[3]

    return when (currentCommand) {
        "phone" -> AddPhone(name, value)
        "email" -> AddEmail(name, value)
        else -> Error("Unknown Command")
    }
}

fun main() {
    var programGo = true
    var command: Command

    while (programGo) {
        command = readCommand()

        if (command.isValid()) {
            when (command) {
                is Error -> printError(command.error)
                is AddEmail -> {
                    person.name = command.name
                    person.email = command.email
                }
                is AddPhone -> {
                    person.name = command.name
                    person.phone = command.phone
                }
                is Exit -> programGo = false
                is Show -> printPerson()
                else -> printHelp()
            }
        } else {
            printError("Argument is not correct")
        }
    }

    println("Программа завершена")
}

fun printError(error: String) {
    println("Error! $error")
}

fun printPerson() {
    if (person.name.isBlank()) {
        println("Not initialized")
    } else {
        println(person)
    }
}

fun printHelp() {
    println("Список доступных команд:\n" +
            "exit\n" +
            "help\n" +
            "show\n" +
            "add <Имя> phone <Номер телефона>\n" +
            "add <Имя> email <Адрес электронной почты>" +
            "\n"
    )
}
