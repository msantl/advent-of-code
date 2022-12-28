package `2022_07`

import java.io.File

abstract class FileSystemEntity(val name: String)

class DirEntity(name: String, val parent: DirEntity?) : FileSystemEntity(name) {
    override fun equals(other: Any?): Boolean {
        if (other !is DirEntity) return false
        val otherDirEntity = other as DirEntity
        return otherDirEntity.name == name
    }

    override fun hashCode(): Int {
        return name.hashCode() + parent.hashCode()
    }

    override fun toString(): String {
        return "DIR $name"
    }
}

class FileEntity(name: String, val size: Int) : FileSystemEntity(name) {
    override fun equals(other: Any?): Boolean {
        if (other !is FileEntity) return false
        val otherFileEntity = other as FileEntity
        return otherFileEntity.name == name
    }

    override fun hashCode(): Int {
        return name.hashCode() + size
    }

    override fun toString(): String {
        return "FILE $name [$size]"
    }
}

var fileSizeLookup: MutableMap<DirEntity, Long> = mutableMapOf()

fun calculateTotalSize(fileSystem: MutableMap<DirEntity, MutableSet<FileSystemEntity>>, currentDir: DirEntity): Long {
    var totalSize = 0L
    if (currentDir in fileSizeLookup) {
        return fileSizeLookup.getValue(currentDir)
    }

    for (fsEntity in fileSystem.getOrDefault(currentDir, mutableSetOf())) {
        if (fsEntity is DirEntity) {
            totalSize += calculateTotalSize(fileSystem, fsEntity as DirEntity)
        } else if (fsEntity is FileEntity) {
            totalSize += (fsEntity as FileEntity).size
        }
    }

    fileSizeLookup.put(currentDir, totalSize)
    return totalSize
}

fun first(filename: String) {
    fileSizeLookup = mutableMapOf()
    val input: List<String> = File(filename).readLines()

    val fileSystem = mutableMapOf<DirEntity, MutableSet<FileSystemEntity>>()

    val root = DirEntity("/", null)

    fileSystem.put(root, mutableSetOf())

    var currentDirectory: DirEntity? = null
    var lsCommandActive: Boolean = false

    for (line in input) {
        val lineSegments = line.split(" ")

        if (lineSegments[0] == "$") {
            lsCommandActive = false
            if (lineSegments[1] == "cd") {
                if (lineSegments[2] == "/") {
                    currentDirectory = root
                } else if (lineSegments[2] == "..") {
                    if (currentDirectory == null) {
                        throw Exception("Current directory is unknown")
                    } else if (currentDirectory.parent == null) {
                        throw Exception("${currentDirectory.name} doesn't have a parent dir")
                    } else {
                        currentDirectory = currentDirectory.parent
                    }
                } else {
                    if (currentDirectory == null) {
                        throw Exception("Current directory is unknown")
                    }
                    // a new directory
                    val newDirectory = DirEntity(lineSegments[2], currentDirectory)
                    fileSystem[currentDirectory] =
                        fileSystem.getOrDefault(currentDirectory, mutableSetOf()).plus(newDirectory).toMutableSet()

                    // Change to the new directory
                    currentDirectory = newDirectory

                    // Init the tracker
                    if (newDirectory !in fileSystem) {
                        fileSystem.put(newDirectory, mutableSetOf())
                    }
                }
            } else if (lineSegments[1] == "ls") {
                // begin reading contents of the current directory
                lsCommandActive = true
            } else {
                throw Exception("Unexpected command ${lineSegments[1]}")
            }
        } else if (lsCommandActive) {
            if (currentDirectory == null) {
                throw Exception("Current directory is unknown")
            }
            val fsEntity =
                if (lineSegments[0] == "dir") {
                    DirEntity(lineSegments[1], currentDirectory)
                } else {
                    FileEntity(lineSegments[1], lineSegments[0].toInt())
                }

            fileSystem[currentDirectory] =
                fileSystem.getOrDefault(currentDirectory, mutableSetOf()).plus(fsEntity).toMutableSet()

        } else {
            throw Exception("Unexpected command ${line}")
        }
    }

    val totalFileSize = calculateTotalSize(fileSystem, root)

    val sol = fileSizeLookup.values.filter { it <= 100000 }.sum()
    println(sol)
}

first("test.in")
second("test.in")

fun second(filename: String) {
    val root = DirEntity("/", null)
    // Here we expect the [fileSizeLookup] is populated from the previous run
    val totalDiskSize = 70000000L
    val requiredSpace = 30000000L

    val freeSpace = totalDiskSize - fileSizeLookup.getValue(root)
    val neededToDelete = requiredSpace - freeSpace

    var dirSizeToDelete : Long? = null
    for (dirSize in fileSizeLookup.values) {
        if (dirSize >= neededToDelete && (dirSizeToDelete == null || dirSizeToDelete > dirSize)) {
            dirSizeToDelete = dirSize
        }
    }

    println(dirSizeToDelete)
}

first("first.in")
second("first.in")
