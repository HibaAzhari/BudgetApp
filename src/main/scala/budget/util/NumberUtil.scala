package budget.util

/**
* To handle String to Double conversions
**/
object NumberUtil {
    def parseDouble(s: String) = try { Some(s.toDouble) } catch { case _ : Throwable => None }
}