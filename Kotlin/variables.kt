val PI = 3.14
var x = 0

fun main(){

	println("x = $x; PI = $PI")
	println("incrementX()")
	println("x = ${incrementX()}; PI = $PI")
}

fun incrementX(): Int{
	return x+1
}
