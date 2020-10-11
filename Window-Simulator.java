import java.util.*;
public class Solution {
      private static class Wind {
            boolean isEmpty;
            int index;
            int timeLeft;

            public Wind(int index, int timeLeft) {
                  isEmpty = true;
                  this.index = index;
                  this.timeLeft = timeLeft;
            }
      }

      private static class Node {
            int val;
            Node prev;
            Node next;
            int serviceTime;
            
            public Node(int val, int serviceTime) {
                  this.val = val;
                  prev = null;
                  next = null;
                  this.serviceTime = serviceTime;
            }
      }
      private static PriorityQueue<Wind> pq = new PriorityQueue<Wind>(new Comparator<Wind>() {
            public int compare(Wind c1, Wind c2) {
                  if (!c1.isEmpty && !c2.isEmpty) {
                        return Integer.compare(c1.timeLeft, c2.timeLeft);
                  } else if (c1.isEmpty && c2.isEmpty) {
                        return Integer.compare(c1.index, c2.index);
                  } else {
                      if (c1.isEmpty)
                        return -1;
                      else {
                         return 1;
                      }
                  } 
            }
      });

      private static Node head = new Node(1, 0);
      private static Node tail = new Node(1, 0);
      private static int qLen = 0;

      private static void add(Node cur) {
            cur.prev = tail.prev;
            cur.prev.next = cur;
            cur.next = tail;
            tail.prev = cur;
            qLen++;
      }

      private static void remove(Node cur) {
            cur.prev.next = cur.next;
            cur.next.prev = cur.prev;
            cur.next = null;
            cur.prev = null;
            qLen--;
      }


      public static int[] solve(String[] cus, int numWind, int qSize) {
            int[] ans = new int[numWind+1];
            head.next = tail;
            tail.prev = head;

            for (int i = 0; i < numWind; i++) {
                  Wind w = new Wind(i, 0);
                  pq.add(w);
            }

            int timeBefore = 0;
            int i = 0;
            while (i < cus.length) { 
                
                for (Wind w : pq) {
                    System.out.println(w.index + " " + w.timeLeft);
                }System.out.println(head.next.val + " " + head.next.serviceTime );
                System.out.println();
                
                
                
                  Node cur = head.next;

                  String[] times = cus[i].split(",");
                  int arrivalTime = Integer.parseInt(times[0]);
                  int serviceTime = Integer.parseInt(times[1]);
                  int patienceTime = Integer.parseInt(times[2]);
                  int nextCusTime = arrivalTime - timeBefore;

                  //when the waiting Q is emtpy, update current windows and 
                  //the new comers either go to a window or the Q
                  if (qLen == 0) {
                        if (nextCusTime != 0) {
                              for (Wind w : pq) {
                                    if (!w.isEmpty) {
                                          w.timeLeft -= nextCusTime;
                                          if (w.timeLeft <= 0) {
                                                w.isEmpty = true;
                                                w.timeLeft = 0;
                                          }
                                    }
                              }
                        }
                        //goes right into the PQ if one of the windows is available
                        if (pq.peek().isEmpty) {
                              Wind w = pq.poll();
                              w.isEmpty = false;
                              w.timeLeft = serviceTime;
                              pq.add(w);

                              ans[w.index + 1]++;
                              ans[0]++;
                        } else {
                              //else go into the waiting Q
                              Node n = new Node(patienceTime, serviceTime);
                              add(n);
                        }
                        i++;
                        timeBefore = arrivalTime;

                  } else { 
                        int leastTimeLeft = pq.peek().timeLeft;

                        //window becomes available first
                        //update all windows
                        //and move as many as customers to the windows
                        if (leastTimeLeft <= cur.val && leastTimeLeft <= nextCusTime) {
                              timeBefore += leastTimeLeft;
                              for (Wind w : pq) {
                                    w.timeLeft -= leastTimeLeft;
                                    if (w.timeLeft == 0) {
                                          w.isEmpty = true;
                                    }
                              }
                              Node tmp = cur;
                              while (pq.peek().isEmpty && tmp != tail) {
                                    Wind w = pq.poll();
                                    w.isEmpty = false;
                                    w.timeLeft = tmp.serviceTime;
                                    pq.add(w);

                                    ans[w.index + 1]++;
                                    ans[0]++;
                                    
                                    tmp = tmp.next;
                                    remove(tmp.prev);
                              }
                        } 
                        
                        // next customer gets in first
                        // deduct time passed from both the pq and q
                        else if (nextCusTime <= cur.val && nextCusTime < leastTimeLeft) {
                              for (Wind w : pq) {
                                    w.timeLeft -= nextCusTime;
                              }
                                
                              //check if anyone is leaving before the new customer arrives
                              Node tmp = cur;
                              while (tmp != tail) {
                                    tmp.val -= nextCusTime;
                                    tmp = tmp.next;
                                    if (tmp.prev.val == 0) {
                                          remove(tmp.prev);
                                    }
                              }
                              //if theres a spot in the Q, add the new guy in
                              if (qLen < qSize) {
                                  add(new Node(patienceTime, serviceTime));
                              }
                              i++;
                              timeBefore = arrivalTime;
                        }
                        
                        //Someone from the Q leaves first 
                        //update the windows 
                        //remove all customers with the same patience time left
                        else if (cur.val < nextCusTime && cur.val < leastTimeLeft) {
                              timeBefore += cur.val;
                              for (Wind w : pq) {
                                    w.timeLeft -= cur.val;
                              }

                              Node tmp = cur;
                              while (tmp != tail) {
                                    tmp.val -= cur.val;
                                    tmp = tmp.next;
                                    if (tmp.prev.val <= 0) {
                                          remove(tmp.prev);
                                    }
                              }
                        }
                  }
            }

            for (Wind w : pq) {
                System.out.println(w.index + " " + w.timeLeft);
            }
            System.out.println(head.next.val + " " + head.next.serviceTime );
            System.out.println(qLen + " ppl in Q");
            System.out.println();
            
            //clean up the rest of the customers waiting in the Q
            while (qLen != 0) {
                  Node cur = head.next;
                  if (pq.peek().isEmpty) {
                        Wind w = pq.poll();
                        w.isEmpty = false;
                        w.timeLeft = cur.serviceTime;
                        pq.add(w);
                        ans[w.index+1]++;
                        ans[0]++;
                        remove(cur);
                        continue;
                  }

                  //if the window becomes available before the customer leaves
                  if (cur.val >= pq.peek().timeLeft) {
                      System.out.println("windowns first");
                        int least = pq.peek().timeLeft;
                        for (Wind w : pq) {
                              w.timeLeft -= least;
                              if (w.timeLeft <= 0) {
                              w.isEmpty = true;
                              w.timeLeft = 0;
                              }
                        }
                    
                        while (cur != tail) {
                              cur.val -= least;
                              cur = cur.next;
                        }

                  } else {
                        //customers leave before the windows become available 
                        System.out.println("q guy first");
                        int val = cur.val;
                        for (Wind w : pq) {
                              w.timeLeft -= val;
                        }
                        while (cur != tail) {
                              cur.val -= val;
                              cur = cur.next;
                              if (cur.prev.val <= 0) {
                                    remove(cur.prev);
                              }
                        }
                  }
            }
            return ans;
      }
      
      public static void main(String[] arg) {
          String[] eg = {"0,25,600", "5,20,600", "5,20,5", "5,50,100", "15,100,1"};
          int[] ans = solve(eg, 2, 1);
          for (int i : ans) {
              System.out.println(i);
          }
      }
}