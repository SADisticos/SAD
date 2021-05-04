fun getStringLength(obj: Any): Int? {
	// 'obj' is automatically cast to 'String' on the right-hand side of '&&'
	if (obj is String && obj.length > 0){
		return obj.length
	}

	return null
}

fun main(){
	fun printLength(obj: Any){
		println("Getting the length o '$obj'. Result: ${getStringLength(obj) ?: "Error: The object is not a string"} ")
	}
	printLength("Incomprenhensibilities")
	printLength(1000)
	printLength(listOf(Any()))
}
