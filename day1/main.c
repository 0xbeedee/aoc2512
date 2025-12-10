#include <stdlib.h>

#include "lib.h"

int main()
{
    // start at 50
    struct Dial dial = {.current_value = 50, .max_value = 99};

    FILE *file_ptr = fopen("rotations.txt", "r");
    if (file_ptr == NULL)
    {
        printf("Error opening file.");
        exit(-1);
    }

    size_t num_rotations = 0;
    int *rotations = parse_rotation_file(file_ptr, &num_rotations);
    fclose(file_ptr);

    size_t num_zero_hits = 0;
    for (size_t i = 0; i < num_rotations; i++)
    {
        turn_dial(&dial, rotations[i]);
        // printf("%zu => %d ", i, rotations[i]);
        // printf("Dial: %d\n", dial.current_value);
        // check if zero was hit after each turn
        if (dial.current_value == 0)
            num_zero_hits++;
    }

    printf("[PHASE 1] Number of times zero was hit on the dial (after the rotations): %zu\n", num_zero_hits);
    reset_dial(&dial);

    return 0;
}
