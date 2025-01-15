/**
 * Start from the left bit(least significant bit)
 * if the char is '0' => double the number at hand
 * if '1' => 1 + (2 * number)
 *
 * See: Binary to Decimal Doubling Method
 *
 * @param binary number
 * @return dec(bin)
 */


def parseLong: List[Char] => Long = bs => {
  if bs == Nil then 0
  else if bs.reverse.head == '0' then 2 * parseLong(bs.reverse.drop(1).reverse) // or simply use dropRight(1)
  else 1 + 2 * parseLong(bs.reverse.drop(1).reverse) // didn't use dropRight because nicht in Uebung bekannt gegeben
}