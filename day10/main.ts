import { readFileSync } from "node:fs";
import { parseInput, lightsBFS, buildILPModel } from "./lib.ts";
import solver from "javascript-lp-solver";

const input = readFileSync("machines.txt", "utf-8");
const machines = parseInput(input);

const phase1Result = machines.map(lightsBFS).reduce((sum, x) => sum + x, 0);
console.log("[PHASE 1] Sum of the fewest number of button presses for lights:", phase1Result);

const phase2Result = machines
  .map((machine) => {
    const model = buildILPModel(machine);
    const solution = solver.Solve(model);
    return solution.result;
  })
  .reduce((sum, minPresses) => sum + minPresses, 0);
console.log("[PHASE 2] Sum of fewest number of button presses for joltages:", phase2Result);
