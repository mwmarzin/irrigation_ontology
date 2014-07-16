package irrigation.abox;

public enum CropType
{
    /**
     * EnumName (daily gallons used, max dry weight, wilting point)
     */
    Apple (5, 1, 7.2, 2, 5),
    Blueberry (5, 1, 7.2, 8, 12),
    Corn (5, 1, 7.2, 4, 10),
    Tomato (5, 1, 7.2, 5, 9),
    Strawberry (5, 1, 7.2, 6, 18);
    
    public double dailyGallonsUsed; 
    public double maxDryWeight;
    public double wiltingPoint;
    public double lowerThreshold;
    public double upperThreshold;
    
    private CropType( double dailyGallonsUsed, 
            double maxDryWeight, 
            double wiltingPoint, 
            double lowerThreshold, 
            double upperThreshold)
    {
        this.dailyGallonsUsed = dailyGallonsUsed;
        this.maxDryWeight = maxDryWeight;
        this.wiltingPoint = wiltingPoint;
    }
}
