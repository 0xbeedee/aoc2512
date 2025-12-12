use day3::gen_max_right;
use day3::max_joltage_in_bank;

use std::fs::read_to_string;

fn main() {
    let banks: Vec<String> = read_to_string("banks.txt")
        .unwrap()
        .lines()
        .map(String::from)
        .collect();

    let mut max_joltages: Vec<i32> = vec![];
    for bank in banks {
        let chars: Vec<char> = bank.chars().collect();
        let max_right = gen_max_right(&chars);
        max_joltages.push(max_joltage_in_bank(&chars, &max_right));
    }

    // println!("{:?}", max_joltages);
    println!(
        "[PHASE 1] Max joltages sum: {}",
        max_joltages.iter().sum::<i32>()
    );
}
