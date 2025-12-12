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

// Finds (and returns) the maximum possible joltage in a single bank, for part 1.
pub fn max_joltage_in_bank_part1(bank: &[char], max_right: &[char]) -> u64 {
    let mut s = String::new();
    let mut max_joltage: u64 = 0;

    for i in 0..(bank.len() - 1) {
        let tens = bank[i].to_digit(10).unwrap() as u64;
        let units = max_right[i + 1].to_digit(10).unwrap() as u64;
        let number: u64 = tens * 10 + units;
        if number > max_joltage {
            max_joltage = number;
        }
        s.clear();
    }

    max_joltage
}

// Finds (and returns) the maximum possible joltage in a single bank, for part 2.
pub fn max_joltage_in_bank_part2(bank: &[char]) -> u64 {
    let mut result = String::new();
    let mut prev_pos: isize = -1;

    for k in 0..12 {
        let start = (prev_pos + 1) as usize;
        let end = bank.len() - (11 - k);

        let max_char = bank[start..end].iter().max().unwrap();
        // always pick the leftmost index
        let max_idx = start + bank[start..end].iter().position(|c| c == max_char).unwrap();

        result.push(bank[max_idx]);
        prev_pos = max_idx as isize;
    }

    result.parse::<u64>().unwrap()
}
