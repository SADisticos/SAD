fun main(){
	val items = listOf("apple", "banana", "kiwifruit")
	val items2 = setOf("apple", "banana", "kiwifruit")
	val fruits = listOf("banana", "avocado", "apple", "kiwifruit")

	for (item in items)
		println(item)

	when{
		"orange" in items2 -> println("juicy")
		"apple" in items2 -> println("apple is fine too")
	}

	fruits
		.filter{it.startsWith("a")}
		.sortedBy{it}
		.map{it.toUpperCase()}
		.forEach{println(it)}
}
