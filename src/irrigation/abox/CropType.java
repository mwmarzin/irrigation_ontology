package irrigation.abox;

public enum CropType
{
    /**
     * EnumName (daily gallons used, max dry weight, wilting point)
     */
    Apple (5, 1, 7.2),
    Blueberry (5, 1, 7.2),
    Corn (5, 1, 7.2),
    Tomato (5, 1, 7.2),
    Strawberry (5, 1, 7.2);
    
    public double dailyGallonsUsed; 
    public double maxDryWeight;
    public double wiltingPoint;
    
    private CropType( double dailyGallonsUsed, double maxDryWeight, double wiltingPoint)
    {
        this.dailyGallonsUsed = dailyGallonsUsed;
        this.maxDryWeight = maxDryWeight;
        this.wiltingPoint = wiltingPoint;
    }
}
