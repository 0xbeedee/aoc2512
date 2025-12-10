#include <stdlib.h>

#include "lib.h"

// Turns the dial by the specified amount.
// Note that this function actually simulates the dial turning (instead of using modulare arithmetic, e.g.).
// This approach is slower but simpler. It also lends itself very well to phase 2.
void turn_dial(Dial *self, int rotation_amount, size_t *n_zeros_hit)
{
    for (int i = 0; i < abs(rotation_amount); i++)
    {
        if (self->current_value == 0)
            *n_zeros_hit += 1;

        if (rotation_amount < 0)
        {
            // left rotation
            if (self->current_value == 0)
                self->current_value = self->max_value;
            else
                self->current_value -= 1;
        }
        else
        {
            // right rotation
            self->current_value += 1;
            if (self->current_value > self->max_value)
                self->current_value = 0;
        }
    }
}

// Counts the number of lines in a file, and resets the file pointer afterwards
// N.B.: It does not close the file!
size_t count_lines(FILE *fptr)
{
    char *line = NULL;
    size_t len = 0, num_lines = 0;
    while (getline(&line, &len, fptr) != -1)
        num_lines++;
    free(line);

    // rewind the pointer in case the file needs to be read again
    rewind(fptr);
    return num_lines;
}

// Parses the file with the various rotations, returning an int array.
// Left rotations are negative numbers, right rotations are positive numbers.
//
// The function assumes that the file is a newline separate list of rotations, each preceded by "L" (left) or "R" (right)
// and followed by the rotation amount (with no space in between).
int *parse_rotation_file(FILE *fptr, size_t *num_rotations)
{
    // first pass: get the number of lines for malloc
    size_t num_lines = count_lines(fptr);
    int *rotations = malloc(num_lines * sizeof(int));
    if (rotations == NULL)
    {
        printf("Error allocating memory for the rotations.");
        exit(-1);
    }

    // second pass: parse the file
    char *line = NULL;
    size_t len = 0, i = 0;
    int number;
    while (getline(&line, &len, fptr) != -1)
    {
        // get the number from line[1:]
        number = (int)strtol(line + 1, NULL, 10);
        rotations[i] = (line[0] == 'L') ? -number : number;
        i++;
    }
    free(line);

    *num_rotations = num_lines;
    return rotations;
}