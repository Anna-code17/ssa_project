public interface Policy {
    
    String getName();
    
    default int getPercentBudget() { 
        return 0; 
    }
    default int getPercentPopulation() { 
        return 0; 
    }
    default int getPercentPollution() { 
        return 0; 
    }
    default int getPercentHappiness() { 
        return 0; 
    }
}
