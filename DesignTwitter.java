/* Time Complexity:
follow() = O(1)
unfollow() = O(1)
postTweet() = O(1)
getNewsFeed() = O(N)
*/

class Twitter {    
    class Tweet{
        int tid;
        int createdAt;
        Tweet(int tid, int time){
            this.tid = tid;
            this.createdAt = time;
        }
    }
    
    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);  //It is required for implementing getNewsFeed()
        if (!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId,time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        HashSet<Integer> usersInFeed = followed.get(userId);  ////// is it contains this.userId
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)-> a.createdAt - b.createdAt);
        
        if(usersInFeed != null){
            for (int followedId : usersInFeed){
                List<Tweet> fTweets = tweets.get(followedId);
                if(fTweets!= null){
                    for (Tweet ftweet : fTweets){
                        pq.add(ftweet);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().tid);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        // No need to check for duplicate followeeId. HashSet takess care of it.
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId != followeeId){
            followed.get(followerId).remove(followeeId);
        }   
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
