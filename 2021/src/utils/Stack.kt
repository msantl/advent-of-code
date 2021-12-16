package utils

class Stack<T : Any>() {
    private val storage = arrayListOf<T>()

    override fun toString() = buildString {
        println("----top----")
        storage.asReversed().forEach {
            println("$it")
        }
        println("-----------")
    }

    val isEmpty: Boolean
        get() = count == 0

    fun push(element: T) {
        storage.add(element)
    }

    fun pop(): T? {
        if (isEmpty) {
            return null
        }
        return storage.removeAt(count - 1)
    }

    fun peek(): T? {
        return storage.lastOrNull()
    }

    private val count: Int
        get() = storage.size
}