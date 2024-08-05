package seamfinding;

import seamfinding.energy.EnergyFunction;
import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {
        int height = picture.height();
        int width = picture.width();
        List<Integer> seam = new ArrayList<>(height);
        Double[][] energyMatrix = new Double[height][width];

        populateEnergyMatrix(energyMatrix, picture, f);
        findSeam(seam, energyMatrix, picture);

        return seam;
    }

    private void populateEnergyMatrix(Double[][] energyMatrix, Picture picture, EnergyFunction f) {
        int width = picture.width();
        int height = picture.height();

        // Initialize the last column of the energy matrix
        for (int y = 0; y < height; y++) {
            energyMatrix[y][width - 1] = f.apply(picture, width - 1, y);
        }

        // Populate the rest of the energy matrix
        for (int x = width - 2; x >= 0; x--) {
            for (int y = 0; y < height; y++) {
                double minimumEnergy = Double.MAX_VALUE;
                for (int offsetY = -1; offsetY <= 1; offsetY++) {
                    int neighborY = y + offsetY;
                    if (neighborY >= 0 && neighborY < height) {
                        double currentEnergy = energyMatrix[neighborY][x + 1] + f.apply(picture, x, y);
                        if (currentEnergy < minimumEnergy) {
                            minimumEnergy = currentEnergy;
                        }
                    }
                }
                energyMatrix[y][x] = minimumEnergy;
            }
        }
    }

    private void findSeam(List<Integer> seam, Double[][] energyMatrix, Picture picture) {
        int height = picture.height();
        int width = picture.width();
        double minEnergy = Double.MAX_VALUE;
        int minIndex = -1;

        // Find the starting point of the seam
        for (int y = 0; y < height; y++) {
            if (energyMatrix[y][0] < minEnergy) {
                minEnergy = energyMatrix[y][0];
                minIndex = y;
            }
        }
        seam.add(minIndex);

        // Trace the seam through the energy matrix
        for (int x = 1; x < width; x++) {
            double minNeighborEnergy = Double.MAX_VALUE;
            int currentY = minIndex;
            for (int offsetY = -1; offsetY <= 1; offsetY++) {
                int neighborY = currentY + offsetY;
                if (neighborY >= 0 && neighborY < height) {
                    if (energyMatrix[neighborY][x] < minNeighborEnergy) {
                        minNeighborEnergy = energyMatrix[neighborY][x];
                        minIndex = neighborY;
                    }
                }
            }
            seam.add(minIndex);
        }
    }
}
