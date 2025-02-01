/**
 * g is a graph such as List(List((1,1), (3,5)), List((0,1), (2,2), (3,2)))
 * indexes represent the knoten and the entries are (Successor, Cost)
 *
 * Example Function Behaviour:
 * getPossibleEdges(g)(List(0)) == List((0, 1, 1), (0, 3, 5))
 *
 * @return all "touching" kanten of a knoten
 */

// to test:
// val g = List(List((1,1), (3,5)), List((0,1), (2,2), (3,2)), List((1,2), (3,3)), List((0,5), (1,2), (2,3)))
def getPossibleEdges: List[List[(Int, Int)]] => List[Int] => List[(Int, Int, Int)] =
  a1 => rs => for (knot <- rs; possible <- a1(knot)) yield (knot, possible._1, possible._2)

/**
 * Minimaler Spannbaum:
 * mst(f)(st). Wendet Prims Alg. ausgehend vom Knoten st an. f ist eine Funktion(getPossibleEdges).
 * Aus dem Output von f wird Kante mit niedrigsten Kosten ausgewaehlt und zum Ergbenis hinzugefuegt.
 * Funktion minBy der Klasse List verwenden. def minBy[B](f: (A) => B)(implicit cmp: math.Ordering[B]): A
 * Der Zielknoten dieser Kante gilt dann als verarbeitet.
 * Das Vorgehen wird, solange wiederholt, bis f keine Kante mehr zurueckgibt.
 *
 * Example Behaviour:
 * mst(getPossibleEdges(g))(0) == List((0, 1), (1, 2), (1, 3))
 */
def mst: (List[Int] => List[(Int, Int, Int)]) => Int => List[(Int, Int)] =
  f => st => {
    def mstHelper: (List[Int], List[(Int, Int)]) => List[(Int,Int)] =
      case (handled, baum) if (f(handled).isEmpty) => baum
      case (handled, baum) => {
        val possibleEdges = f(handled).filter { case (_, v, _) => !handled.contains(v) } // Prevent cycles
        if (possibleEdges.isEmpty) baum
        else {
          val minEdge = possibleEdges.minBy { case (_, _, cost) => cost }
          mstHelper(minEdge._2 :: handled, (minEdge._1, minEdge._2) :: baum)
        }  
      }
    mstHelper(List(st), List())
  }

