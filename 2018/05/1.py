def find_and_remove(poly):
    if len(poly) == 1:
        return poly

    res = ""
    i = 0
    while i < len(poly):
        if i + 1 < len(poly) and poly[i].lower() == poly[i+1].lower() and poly[i] != poly[i+1]:
            i += 1
        else:
            res += poly[i]

        i += 1
    return res

def main():
    with open("1.in") as f:
        curr = f.readline().strip()
        prev = None
        while curr != prev:
            prev = curr
            curr = find_and_remove(curr)

        print(len(curr))


if __name__=="__main__":
    main()
