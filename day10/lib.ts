type Machine = { target_lights: string; buttons: number[][]; target_joltages: number[] };

/** Parses the input file, building an array of Machine objects. */
export function parseInput(input: string): Machine[] {
  let machines: Machine[] = [];
  let lines = input.trim().split("\n");

  lines.forEach((line) => {
    // first part is for the indicator lights - [...]
    const target_lights = line.slice(line.indexOf("[") + 1, line.indexOf("]"));

    // second part is for the buttons - (...)
    const buttons = [...line.matchAll(/\(([^)]+)\)/g)].map((m) => m[1].split(",").map(Number));

    // third part is for the joltages - {...}
    const target_joltages = line
      .slice(line.indexOf("{") + 1, line.indexOf("}"))
      .split(",")
      .map(Number);

    machines.push({ target_lights, buttons, target_joltages });
  });

  return machines;
}

/** Performs BFS on the state search tree made up of light indicators. It operates on a single Machine.
 *
 * Returns the fewest button presses necessary to get to the target light configuration.
 */
export function lightsBFS(machine: Machine): number {
  let init_lights = ".".repeat(machine.target_lights.length); // all lights off

  const queue: [string, number][] = [];
  queue.push([init_lights, 0]);
  let visited = new Set<string>();
  visited.add(init_lights);

  while (queue.length > 0) {
    const [curState, depth] = queue.shift()!;
    if (curState === machine.target_lights) {
      return depth;
    }

    for (const button of machine.buttons) {
      const newState = toggleLights(curState, button);
      if (!visited.has(newState)) {
        visited.add(newState);
        queue.push([newState, depth + 1]);
      }
    }
  }

  return -1;
}

/** Sets up the integer linear programming (ILP) problem for each machine. */
export function buildILPModel(machine: Machine) {
  // see https://www.npmjs.com/package/javascript-lp-solver
  const model: {
    optimize: string;
    opType: string;
    constraints: Record<string, { equal: number }>;
    variables: Record<string, Record<string, number>>;
    ints: Record<string, number>;
  } = {
    optimize: "presses",
    opType: "min",
    constraints: {},
    variables: {},
    ints: {},
  };

  machine.target_joltages.forEach((targetValue, counterIndex) => {
    model.constraints[`counter${counterIndex}`] = { equal: targetValue };
  });

  machine.buttons.forEach((button, buttonIndex) => {
    const buttonName = `button${buttonIndex}`;
    model.variables[buttonName] = {
      presses: 1, // Every button contributes 1 to the objective
    };

    button.forEach((counterIndex) => {
      model.variables[buttonName][`counter${counterIndex}`] = 1;
    });
  });

  // all buttons must have integer values
  machine.buttons.forEach((button, buttonIndex) => {
    model.ints[`button${buttonIndex}`] = 1;
  });

  return model;
}

/** Toggles the lights on/off for a single state and a single group of button presses. */
function toggleLights(state: string, indices: number[]): string {
  for (const idx of indices) {
    state = state.slice(0, idx) + (state[idx] === "#" ? "." : "#") + state.slice(idx + 1);
  }
  return state;
}
