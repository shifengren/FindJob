package algorithm.linearstruct;

/**
 * 微软亚院之编程
 * 判断俩个链表是否相交
   给出俩个单向链表的头指针，比如h1，h2，判断这俩个链表是否相交。
   为了简化问题，我们假设俩个链表均不带环。

   问题扩展：
 1.如果链表可能有环列?
 2.如果需要求出2个链表相交的第一个节点?


 详解见博客
 http://www.jianshu.com/p/9a953e4ad2cd
 */
public class ListIntersect {

    static class ListNode{
        int value;
        ListNode next;
    }

    static ListNode initList(int val){
        ListNode header = new ListNode(); // header
        header.value = val;
        return header;
    }

    static boolean isIntersect1(ListNode h1, ListNode h2){
        boolean isinter = false;
        ListNode p1 = h1, p2 = h2;
        if(p1==null || h2==null) return false;
        // find the end node of list p1
        while(p1.next != null) p1 = p1.next;

        // append list p2 on the tail of p1
        p1.next = p2;

        // enumerate list p2 from its header
        while(p2 != null){
            if(p2 == h2) {
                isinter = true;
                break;
            }
            p2 = p2.next;
        }

        return isinter;
    }


    static boolean isIntersect2(ListNode h1, ListNode h2){
        ListNode p1 = h1, p2 = h2;
        if(p1==null || h2==null) return false;
        ListNode last1 = p1;
        while(p1.next != null){
            last1 = p1;
            p1 = p1.next;
        }

        ListNode last2 = p2;
        while (p2.next!=null){
            last1 = p2;
            p2 = p2.next;
        }
        if(last1==last2){
            return true;
        }else return false;
    }

    static ListNode findFisrtCrossNode(ListNode h1, ListNode h2){
        int lenA = len(h1);
        int lenB = len(h2);
        ListNode p1 = h1, p2 = h2;
        ListNode tmp = null;
        if(lenA > lenB) tmp = p1;
        else tmp = p2;
        for(int i=0; i<Math.abs(lenA-lenB); ++i){
            tmp = tmp.next;
        }
        if(lenA > lenB) p1=tmp;
        else p2 = tmp;

        while(p1!=null && p2!=null){
            if(p1==p2) return p1;
            p1 = p1.next;
            p2 = p2.next;
        }
        return null;
    }

    static int len(ListNode h){
        int clen = 0;
        ListNode p = h;
        while (p!=null){
            p = p.next;
            ++clen;
        }
        return clen;
    }

    static boolean hasCycle(ListNode h){
        ListNode fast=h, slow=h;

        while(fast!=null && fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast==slow && slow!=null){
                return true;
            }
        }
        return false;
    }

    static ListNode findCycleEntry(ListNode h){
        ListNode fast=h, slow=h;
        ListNode meetNode = null;
        while(fast!=null && fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast==slow && slow!=null){
                meetNode = slow;
                break;
            }
        }
        ListNode p = h;
        while(p!=null && meetNode!=null){
            if(p==meetNode){
                return p;
            }
            p = p.next;
            meetNode = meetNode.next;
        }
        return null;
    }

    public static void main(String[] args){

    }

}
