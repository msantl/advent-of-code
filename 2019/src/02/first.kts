import java.io.File

fun main() {
    var input = File("first.in").readText()
    var list: MutableList<Int> = input.split(",").map { it.trim() }.map { it.toInt() }.toMutableList()

    list[1] = 12
    list[2] = 2

    var ip: Int = 0
    while (true) {
        var cmd: Int = list.get(ip++)

        if (cmd == 1 || cmd == 2) {
            var id1: Int = list.get(ip++)
            var id2: Int = list.get(ip++)
            var id3: Int = list.get(ip++)

            if (cmd == 1) {
                list[id3] = list.get(id1) + list.get(id2)
            } else if (cmd == 2) {
                list[id3] = list.get(id1) * list.get(id2)
            } else {
                print("Something went wrong!")
                break
            }
        } else if (cmd == 99) {
            break
        }
    }

    print(list[0])
}

main()
