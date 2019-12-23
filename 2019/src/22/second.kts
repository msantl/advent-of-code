import java.io.File
import java.math.BigInteger

class Matrix(_a: BigInteger, _b: BigInteger, _c: BigInteger, _d: BigInteger) {
    var a = _a
    var b = _b
    var c = _c
    var d = _d

    fun mul(other: Matrix, N: BigInteger) {
        val aa = ((a * other.a) % N) + ((b * other.c) % N)
        val bb = ((a * other.b) % N) + ((b * other.d) % N)
        val cc = ((c * other.a) % N) + ((d * other.c) % N)
        val dd = ((c * other.b) % N) + ((d * other.d) % N)

        a = (aa + N) % N
        b = (bb + N) % N
        c = (cc + N) % N
        d = (dd + N) % N
    }
}

fun pow(x: Matrix, N: Long, M: BigInteger): Matrix {
    if (N == 0L) return Matrix(BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE)
    val base = pow(x, N / 2, M)
    base.mul(base, M)
    if (N % 2 == 1L) {
        base.mul(x, M)
    }
    return base
}

fun main() {
    val CNT: Long = 101741582076661L
    val N: BigInteger = BigInteger.valueOf(119315717514047L)
    var card: BigInteger = BigInteger.valueOf(2020L)

    val m: Matrix = Matrix(BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE)

    val actions = File("second.in").readLines()
    for (action in actions) {
        val cmds = action.split(" ")
        if (cmds[0] == "cut") {
            var M = cmds[1].toBigInteger()
            val t = Matrix(BigInteger.ONE, M, BigInteger.ZERO, BigInteger.ONE)
            m.mul(t, N)

        } else if (cmds[1] == "into") {
            // deal into new stack
            val t = Matrix(N.minus(BigInteger.ONE), N.minus(BigInteger.ONE), BigInteger.ZERO, BigInteger.ONE)
            m.mul(t, N)

        } else if (cmds[1] == "with") {
            // deal with increment
            var M = cmds[3].toBigInteger()
            val t = Matrix( M.modInverse(N) , BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE)
            m.mul(t, N)

        }
    }

    var r = pow(m, CNT, N)
    val res = (card * r.a + r.b) % N
    println(res)
    // 79490866971571
}

main()