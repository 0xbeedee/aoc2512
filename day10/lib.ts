type Machine = { target_lights: string; buttons: number[][]; joltages: number[] };

/** Parses the input file, building an array of Machine objects. */
export function parseInput(input: string): Machine[] {
  let machines: Machine[] = [];
  let lines = input.trim().split("\n");

  lines.forEach((line) => {
    // first part is for the indicator lights - [...]
    let target_lights = line.slice(line.indexOf("[") + 1, line.indexOf("]"));
    // second part is for the buttons - (...)
    let button_groups = line.matchAll(/\(([^)]+)\)/g);
    let buttons = [...button_groups].map((match) => match[1]).map((group) => group.split(",").map(Number));
    // third part is for the joltages - {...}
    let joltage_string = line.slice(line.indexOf("{") + 1, line.indexOf("}"));
    let joltages = joltage_string.split(",").map(Number);
    machines.push({ target_lights, buttons, joltages });
  });

  return machines;
}

/** Performs BFS on the state search tree. It operates on a single Machine.
 *
 * Returns the fewest button presses necessary to get to the desired state.
 */
export function BFS(machine: Machine): number {
  let init_lights = ".".repeat(machine.target_lights.length); // all lights off

  const queue: [string, number][] = [];
  queue.push([init_lights, 0]);
  let visited = new Set<string>();
  visited.add(init_lights);

  while (queue.length > 0) {
    const state = queue.shift()!;
    if (state[0] === machine.target_lights) {
      return state[1];
    }

    machine.buttons.forEach((buttons) => {
      let new_state = toggle_lights(state[0], buttons);
      if (!visited.has(new_state)) {
        visited.add(new_state);
        queue.push([new_state, state[1] + 1]);
      }
    });
  }

  // queue exhausted => target state not reachable
  return -1;
}

/** Toggles the lights on/off for a single state, and a single group of buttons. */
function toggle_lights(state: string, buttons: number[]): string {
  buttons.forEach((button) => {
    state = state.slice(0, button) + (state[button] == "#" ? "." : "#") + state.slice(button + 1);
  });
  return state;
}
