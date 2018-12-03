import sys

def get_binary_string(x, length = 4):
    s = "{0:b}".format(x)
    while len(s) < length:
        s = "0" + s
    return s

def main():
    fact_A = 16807
    fact_B = 48271
    modulo = 2147483647

    A = None
    B = None

    for line in sys.stdin:
        line = line.strip().split()

        if A is None:
            A = int(line[4])
        else:
            B = int(line[4])

    match = 0
    for x in range(40000000):
        A = (A * fact_A) % modulo
        B = (B * fact_B) % modulo

        bin_a = get_binary_string(A, 32)
        bin_b = get_binary_string(B, 32)

        if bin_a[16:] == bin_b[16:]:
            match += 1

    print(match)

if __name__=="__main__":
    main()
