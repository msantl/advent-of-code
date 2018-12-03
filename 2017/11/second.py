import sys

def distance(a, b):
    x1, y1, z1 = a
    x2, y2, z2 = b

    return (abs(x1 - x2) + abs(y1 - y2) + abs(z1 - z2)) / 2

def main():
    sol = None
    start = (0, 0, 0)
    offset = {}

    offset["n"] = (1, -1, 0)
    offset["ne"] = (1, 0, -1)
    offset["se"] = (0, 1, -1)
    offset["s"] = (-1, 1, 0)
    offset["sw"] = (-1, 0, 1)
    offset["nw"] = (0, -1, 1)

    for line in sys.stdin:
        line = line.strip().split(",")

        for direction in line:
            dx, dy, dz = offset[direction]
            x,y,z = start

            start = (x+dx, y+dy, z+dz)
            dist = distance((0, 0, 0), start)

            if sol is None or sol < dist:
                sol = dist

    print(sol)

if __name__=="__main__":
    main()
