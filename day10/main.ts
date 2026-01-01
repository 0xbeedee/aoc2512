import { readFileSync } from "node:fs";
import { parseInput, lightsBFS } from "./lib.ts";

const input = readFileSync("machines.txt", "utf-8");
const machines = parseInput(input);

const phase1Result = machines.map(lightsBFS).reduce((sum, x) => sum + x, 0);
console.log("[PHASE 1] Sum of the fewest number of button presses:", phase1Result);

const phase2Result = 0;
console.log("[PHASE 2] Result:", phase2Result);
