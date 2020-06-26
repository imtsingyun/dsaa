package main

import "fmt"

// 树的大小
var size int

// 指向根节点人指针
var root *avlNode

func main() {

	addNode(3)
	addNode(1)
	addNode(2)
	addNode(4)
	addNode(5)

	preorderTraversal(root)

}

/**
 * 添加元素
 */
func addNode(element int) {
	// 根节点为空，表示添加的是地一个元素
	if root == nil {
		root = createNode(element, nil)
		size++
		return
	}
	// 指向根节点开始遍历
	var node *avlNode = root
	// 元素的比较结果，> 0 表示插入右节点， < 0 表示插入左节点
	var compare int
	// 要插入元素的父节点
	var parent *avlNode
	for {
		if node == nil {
			break
		}
		compare = compareTo(element, node.element)
		parent = node
		if compare > 0 {
			node = node.right
		} else if compare < 0 {
			node = node.left
		}
	}
	// 创建新节点用于封装需要插入的元素
	targetNode := createNode(element, parent)
	if compare > 0 {
		parent.right = targetNode
	} else {
		parent.left = targetNode
	}
	size++
}

func compareTo(v1 int, v2 int) int {
	return v1 - v2
}

/**
 * 创建节点
 */
func createNode(element int, p *avlNode) *avlNode {
	var node = avlNode{
		element: element,
		parent:  p,
		height:  1,
	}
	return &node
}

/**
 * 中序遍历
 */
func inorderTraversal(node *avlNode) {
	if node == nil {
		return
	}
	inorderTraversal(node.left)
	fmt.Println(node.element)
	inorderTraversal(node.right)
}

/**
 * 前序遍历
 */
func preorderTraversal(node *avlNode) {
	if node == nil {
		return
	}
	fmt.Println(node.element)
	preorderTraversal(node.left)
	preorderTraversal(node.right)
}

/**
 * 二叉树节点
 */
type avlNode struct {
	element int
	left    *avlNode
	right   *avlNode
	parent  *avlNode
	height  int
}
