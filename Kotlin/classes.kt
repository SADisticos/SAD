class Rectangle(var heigth: Double, var length: Double){
	var perimeter = (heigth + length)*2;

	fun area(): Double{
		return heigth * length
	}
}

fun main(){
	val rectangle = Rectangle(5.0, 2.0)
	println("The perimeter is ${rectangle.perimeter}")
	println("The area is ${rectangle.area()}")
}
