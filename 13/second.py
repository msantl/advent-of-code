import sys

def main():
    scanners = {}
    max_depth = 0
    for line in sys.stdin:
        line = line.strip().split(": ")
        d, r = [int(x) for x in line]
        scanners[d] = r
        max_depth = max(d, max_depth)

    delay = 0
    while True:
        caught = False

        for x in range(max_depth + 1):
            if x not in scanners:
                continue

            scanner_position = 0

            if scanners[x] > 1:
                scanner_position = (x + delay) % (2 * scanners[x] - 2)
                if scanner_position > scanners[x]:
                    scanner_position = 2 * scanners[x] - 2 - scanner_position

            if scanner_position == 0:
                caught = True
                break

        if not caught:
            break

        delay += 1

    print(delay)

if __name__=="__main__":
    main()
