// Generates an array containing, at position i, the highest digit from i to the end of the bank.
// For example, for a bank like "234234234234278", it returns "['8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8']"
pub fn gen_max_right(bank: &[char]) -> Vec<char> {
    let mut max_right = vec![' '; bank.len()];
    let mut current_max = '0';

    for i in (0..bank.len()).rev() {
        let bank_char = bank[i];
        if current_max < bank_char {
            current_max = bank_char;
        }
        max_right[i] = current_max;
    }

    max_right
}

// Finds (and returns) the maximum possible joltage in a single bank (line).
pub fn max_joltage_in_bank(bank: &[char], max_right: &[char]) -> i32 {
    let mut s = String::new();
    let mut max_joltage = 0;

    for i in 0..(bank.len() - 1) {
        let tens = bank[i].to_digit(10).unwrap() as i32;
        let units = max_right[i + 1].to_digit(10).unwrap() as i32;
        let number: i32 = tens * 10 + units;
        if number > max_joltage {
            max_joltage = number;
        }
        s.clear();
    }

    max_joltage
}
