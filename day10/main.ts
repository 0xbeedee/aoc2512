import { readFileSync } from "node:fs";
import { parseInput, BFS } from "./lib.ts";

const input = readFileSync("machines.txt", "utf-8");
const machines = parseInput(input);

const phase1Result = machines.map(BFS).reduce((sum, x) => sum + x, 0);
console.log("[PHASE 1] Sum of the fewest number of button presses:", phase1Result);

console.log("[PHASE 2] Result:", "TODO");
