import sys

def main():
    scanners = {}
    max_depth = 0
    for line in sys.stdin:
        line = line.strip().split(": ")
        d, r = [int(x) for x in line]
        scanners[d] = r
        max_depth = max(d, max_depth)

    sol = 0
    for x in range(max_depth + 1):
        if x not in scanners:
            continue

        scanner_position = 0

        if scanners[x] > 1:
            scanner_position = x % (2 * scanners[x] - 2)
            if scanner_position > scanners[x]:
                scanner_position = 2 * scanners[x] - 2 - scanner_position

        if scanner_position == 0:
            sol += x * scanners[x]

    print(sol)

if __name__=="__main__":
    main()
