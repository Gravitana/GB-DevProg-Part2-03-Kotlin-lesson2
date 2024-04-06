package ru.gravitana.classwork

sealed interface Operation

class Value(val value: Double) : Operation

class Plus(val op1: Operation, val op2: Operation) : Operation

class Multiply(val op1: Operation, val op2: Operation) : Operation

fun calculate(operation: Operation): Double {
    return when (operation) {
        is Multiply -> calculate(operation.op1) * calculate(operation.op2)
        is Plus -> calculate(operation.op1) + calculate(operation.op2)
        is Value -> operation.value
    }
}

fun printOperationResult(operation: Operation) {
    val result = calculate(operation)
    println(result)
}

fun main() {
    // Соответствует формуле 4 + 2.5 * 2
    printOperationResult(
        Plus(
            Value(4.0),
            Multiply(
                Value(2.5),
                Value(2.0)
            )
        )
    )
}
