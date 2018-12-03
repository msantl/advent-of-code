def main():
    sol = None
    with open("2.in") as f:
        lines = f.readlines()

        for i, line1 in enumerate(lines):
            for j, line2 in enumerate(lines):
                if j <= i: continue

                common = ""
                for (a, b) in zip (line1, line2):
                    if a == b:
                        common += a
                if len(common) == len(line1) - 1:
                    sol = common

    print(sol)


if __name__ == "__main__":
    main()
